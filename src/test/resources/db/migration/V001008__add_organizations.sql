-- Update 'project' table
WITH org_id AS (INSERT INTO public.organization (name, slug, organization_type, external_id)
VALUES ('My organization', 'my-organization', 'INTERNAL', 'ext-1') RETURNING id)
UPDATE public.project AS prj SET
  "organization_id" = 1,
  "key" = slugify(name);

UPDATE project SET "slug" = "key";

INSERT INTO organization_user (user_id, organization_id, organization_role)
(SELECT pu.user_id, (SELECT o.id FROM organization o LIMIT 1), (SELECT 'MEMBER'::public."organization_role_enum")
FROM project_user pu
WHERE pu."project_role" IN
  ('OPERATOR'::public."project_role_enum",
   'CUSTOMER'::public."project_role_enum",
   'MEMBER'::public."project_role_enum"
   )
);


INSERT INTO organization_user (user_id, organization_id, organization_role )
(SELECT pu.user_id, (SELECT o.id FROM organization o LIMIT 1), (SELECT 'MANAGER'::public."organization_role_enum")
FROM project_user pu
WHERE pu.project_role = 'PROJECT_MANAGER'::public."project_role_enum");

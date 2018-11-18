/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UnidadDeMedidaComponentsPage, UnidadDeMedidaDeleteDialog, UnidadDeMedidaUpdatePage } from './unidad-de-medida.page-object';

const expect = chai.expect;

describe('UnidadDeMedida e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let unidadDeMedidaUpdatePage: UnidadDeMedidaUpdatePage;
    let unidadDeMedidaComponentsPage: UnidadDeMedidaComponentsPage;
    let unidadDeMedidaDeleteDialog: UnidadDeMedidaDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load UnidadDeMedidas', async () => {
        await navBarPage.goToEntity('unidad-de-medida');
        unidadDeMedidaComponentsPage = new UnidadDeMedidaComponentsPage();
        expect(await unidadDeMedidaComponentsPage.getTitle()).to.eq('Unidad De Medidas');
    });

    it('should load create UnidadDeMedida page', async () => {
        await unidadDeMedidaComponentsPage.clickOnCreateButton();
        unidadDeMedidaUpdatePage = new UnidadDeMedidaUpdatePage();
        expect(await unidadDeMedidaUpdatePage.getPageTitle()).to.eq('Create or edit a Unidad De Medida');
        await unidadDeMedidaUpdatePage.cancel();
    });

    it('should create and save UnidadDeMedidas', async () => {
        const nbButtonsBeforeCreate = await unidadDeMedidaComponentsPage.countDeleteButtons();

        await unidadDeMedidaComponentsPage.clickOnCreateButton();
        await promise.all([unidadDeMedidaUpdatePage.setNombreInput('nombre')]);
        expect(await unidadDeMedidaUpdatePage.getNombreInput()).to.eq('nombre');
        await unidadDeMedidaUpdatePage.save();
        expect(await unidadDeMedidaUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await unidadDeMedidaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last UnidadDeMedida', async () => {
        const nbButtonsBeforeDelete = await unidadDeMedidaComponentsPage.countDeleteButtons();
        await unidadDeMedidaComponentsPage.clickOnLastDeleteButton();

        unidadDeMedidaDeleteDialog = new UnidadDeMedidaDeleteDialog();
        expect(await unidadDeMedidaDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Unidad De Medida?');
        await unidadDeMedidaDeleteDialog.clickOnConfirmButton();

        expect(await unidadDeMedidaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
